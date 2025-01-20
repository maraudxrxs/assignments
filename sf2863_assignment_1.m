birthdate =   20030110 % Write the birth date on format yyyymmdd for oldest member
format compact
[lambda1,lambda2,mu1,mu2,V1,V2,V] = getFerrydata(birthdate);  % Do not clear or redefine these variables.
h=0.001; % Discretization step

%Question 1
% State 1: Both engines are operational
% State 2: Engine 1 is operational, engine 2 fails.
% State 3: Engine 1 fails, engine 2 is operational.
% State 4: Both engines fail.

% Question 2 should be answered in the report, and submitted below

n1_1 = 3;  n2_1 = 0; 
Qi = [
    -(lambda1 + lambda2), lambda1, lambda2, 0;
    n1_1*mu1, -(n1_1*mu1 + lambda2), 0, lambda2;
    3*mu2, 0, -(3*mu2 + lambda1), lambda1;
    0, 3*mu1, 0, -3*mu1
];
Qieig=sort(eig(Qi)); % We compare the eigenvalues
% To make sure that the order of your states will not change the result
n1_2 = 2;  n2_2 = 1;
Qii = [
    -(lambda1 + lambda2), lambda1, lambda2, 0;
    3*mu1, -(3*mu1 + lambda2), 0, lambda2;
    3*mu2, 0, -(3*mu2 + lambda1), lambda1;
    0, n1_2*mu1, n2_2*mu2, -(n1_2*mu1 + n2_2*mu2)
];
Qiieig=sort(eig(Qii));
n1_3 = 1;  n2_3 = 2; 
Qiii = [
    -(lambda1 + lambda2), lambda1, lambda2, 0;
    3*mu2, -(3*mu2 + lambda2), 0, lambda2;
    3*mu1, 0, -(3*mu1 + lambda1), lambda1;
    0, n1_3*mu1, n2_3*mu2, -(n1_3*mu1 + n2_3*mu2)
];
Qiiieig=sort(eig(Qiii));
n1_4 = 0;  n2_4 = 3;
Qiv = [
    -(lambda1 + lambda2), lambda1, lambda2, 0;
    3*mu2, -(3*mu2 + lambda2), 0, lambda2;
    3*mu1, 0, -(3*mu1 + lambda1), lambda1;
    0, 0, 3*mu2, -3*mu2
];
Qiveig=sort(eig(Qiv));

% Question 3 should be answered in the report

% A stationary distribution exists since there is a presence of an
% absobring state, ie state 4. Thus, once the process enters state 4, it
% remains there indefinitely, thus resulting in an absorbing Markov chain.
% Since there are non-zero transition rates lambda1 and lambda2, the ferry
% will eventually end up in the transition state over time.

% Question 4 should be answered in the report, describe how you do it, and  

b = [zeros(4, 1); 1];

Ai = [Qi; ones(1, 4)];
PIi = Ai \ b;
PIisort = sort(PIi);

Aii = [Qii; ones(1, 4)];
PIii = Aii \ b;
PIiisort = sort(PIii);

Aiii = [Qiii; ones(1, 4)];
PIiii = Aiii \ b;
PIiiisort = sort(PIiii);

Aiv = [Qiv; ones(1, 4)];
PIiv = Aiv \ b;
PIivsort = sort(PIiv);


% Question 5 should be answered in the report, describe how you do it, and  

AVi = PIi(1)*V + PIi(2)*V1 + PIi(3)*V2 + PIi(4)*0;
AVii = PIii(1)*V + PIii(2)*V1 + PIii(3)*V2 + PIii(4)*0;
AViii = PIiii(1)*V + PIiii(2)*V1 + PIiii(3)*V2 + PIiii(4)*0;
AViv = PIiv(1)*V + PIiv(2)*V1 + PIiv(3)*V2 + PIiv(4)*0;

AV= [AVi AVii AViii AViv];

% Question 7a should be answered in the report, describe how you do it, and check that the result agrees with the analytic result
% Parameters
t1 = 10000;  % Initial maximum simulation time for the first run
t2 = 20000;  % Initial maximum simulation time for the second run
error_threshold = 0.01;  % Error threshold (1%)
max_iterations = 10;  % Maximum number of iterations to refine T

% Function to run a single simulation
function state_probs = run_simulation(t_max, lambda1, lambda2, mu1, mu2)
    state_counts = [0, 0, 0, 0];  % Time spent in each state
    current_time = 0;
    current_state = 0;  % Start in state 0
    
    while current_time < t_max
        % Determine rates and transition probabilities for the current state
        switch current_state
            case 0  % Both engines operational
                rate_sum = lambda1 + lambda2;
                transition_probs = [lambda1, lambda2];
            case 1  % Engine 1 failed
                rate_sum = 3 * mu1 + lambda2;
                transition_probs = [3 * mu1, lambda2];
            case 2  % Engine 2 failed
                rate_sum = 3 * mu2 + lambda1;
                transition_probs = [3 * mu2, lambda1];
            case 3  % Both engines failed
                break;
        end
        
        % Time to next transition
        dwell_time = -log(rand) / rate_sum;
        current_time = current_time + dwell_time;
        
        % Update time spent in current state
        state_counts(current_state + 1) = state_counts(current_state + 1) + dwell_time;
        
        % Transition to the next state
        cumulative_probs = cumsum(transition_probs) / rate_sum;
        next_state = find(cumulative_probs >= rand, 1, 'first') - 1;
        current_state = next_state;
    end
    
    % Calculate stationary probabilities
    state_probs = state_counts / t_max;
end

% Run the first simulation
state_probs_1 = run_simulation(t1, lambda1, lambda2, mu1, mu2);

% Iteratively run the second simulation and refine T
iteration = 0;
converged = false;

while ~converged && iteration < max_iterations
    state_probs_2 = run_simulation(t2, lambda1, lambda2, mu1, mu2);
    
    % Calculate the maximum error between the two sets of probabilities
    max_error = max(abs(state_probs_2 - state_probs_1));
    
    if max_error < error_threshold
        converged = true;  % The error is below the threshold
    else
        t2 = t2 * 2;  % Increase T for the second simulation
        state_probs_1 = state_probs_2;  % Update the reference probabilities
    end
    
    iteration = iteration + 1;
end

% Output results
disp('First simulation probabilities:');
disp(state_probs_1);
disp('Second simulation probabilities:');
disp(state_probs_2);
disp(['Final simulation time (T2): ', num2str(t2)]);
disp(['Maximum error between simulations: ', num2str(max_error)]);

% Question 8a should be answered in the report, describe how you do it, do the calculations and enter results below

% Parameters
num_simulations = 10000;  % Number of simulations
time_to_failure = zeros(num_simulations, 1);  % Array to store time to failure for each simulation

% Pre-calculate transition rates and probabilities for each state
% Define states: 0 (both engines operational), 1 (only engine 1 operational),
%                2 (only engine 2 operational), 3 (both engines failed)

% Transition rates from each state
rate_sums = [lambda1 + lambda2, 3*mu1 + lambda2, 3*mu2 + lambda1, 0];
transition_probs = {
    [lambda1 / (lambda1 + lambda2), lambda2 / (lambda1 + lambda2)], 
    [3*mu1 / (3*mu1 + lambda2), lambda2 / (3*mu1 + lambda2)],       
    [3*mu2 / (3*mu2 + lambda1), lambda1 / (3*mu2 + lambda1)]         
};

% Simulation loop
for i = 1:num_simulations
    current_time = 0;
    current_state = 0;  % Start at state 0 (both engines operational)
    
    while current_state < 3
        % Get the rate sum and transition probabilities for the current state
        rate_sum = rate_sums(current_state + 1);
        
        % Determine time to next transition (exponentially distributed)
        dwell_time = -log(rand) / rate_sum;
        current_time = current_time + dwell_time;
        
        % Determine the next state based on transition probabilities
        if current_state == 0
            next_state = 1 + (rand >= transition_probs{1}(1));  % 1 if random < prob(1), else 2
        elseif current_state == 1
            next_state = 3 * (rand >= transition_probs{2}(1));  % 0 if random < prob(1), else 3
        elseif current_state == 2
            next_state = 3 * (rand >= transition_probs{3}(1));  % 0 if random < prob(1), else 3
        end
        current_state = next_state;
    end
    
    % Store the time to failure for this simulation run
    time_to_failure(i) = current_time;
end

% Calculate the average time to failure
AVtTF = mean(time_to_failure);  % Average time to failure
disp(['Average Time to Failure (T = 0 to both engines fail): ', num2str(AVtTF)]);



P = [0.95, 0.02, 0.03, 0;
     0.03, 0.94, 0, 0.03;
     0.045, 0, 0.935, 0.02;
     0, 0, 0, 1];

% Question 7b should be answered in the report, describe how you do it, and check that the result agrees with the analytic result

% Parameters
N = 100000;
num_states = 4; 

%transition matrix P
P = [
    0.95, 0.02, 0.03, 0;   
    0.03, 0.94, 0, 0.03;
    0.045, 0, 0.935, 0.02;
    0, 0, 0, 1             % Absorbing state
];

% Initialize state tracking
state_counts1 = zeros(1, num_states);
state_counts2 = zeros(1, num_states);

%run two separate simulations
for run = 1:2
    current_state = 1;  
    state_counts = zeros(1, num_states);

    for step = 1:N
        %track the time spent in each state
        state_counts(current_state) = state_counts(current_state) + 1;

        %determine the next state based on P
        next_state = find(cumsum(P(current_state, :)) >= rand, 1, 'first');
        current_state = next_state;
    end
    
    if run == 1
        state_counts1 = state_counts;
    else
        state_counts2 = state_counts;
    end
end

% time-averaged probabilities
state_probs1 = state_counts1 / N;
state_probs2 = state_counts2 / N;

% Display results
disp('Estimated stationary probabilities for simulation 1:');
disp(state_probs1);

disp('Estimated stationary probabilities for simulation 2:');
disp(state_probs2);

% Check convergence
disp('Difference between the two simulations:');
disp(abs(state_probs1 - state_probs2));

% Question 8a should be answered in the report, describe how you do it, do the calculations and enter results below

Estimated_speed = 0.0005375
% Question 9a should be answered in the report, describe how you do it, do the calculations and enter results below

P = [
    0.95, 0.02, 0.03, 0;
    0.03, 0.94, 0, 0.03;
    0.045, 0, 0.935, 0.02;
    0, 0, 0, 1   
];


x0 = [1, 0, 0, 0];

x10 = x0 * (P^10);

prob_state3_after_10_steps = x10(4);

Probfail10 = prob_state3_after_10_steps

Q = P(1:3, 1:3);

I = eye(3);

N = inv(I - Q);

mean_steps_to_state3 = sum(N(1, :));

ETtTF = mean_steps_to_state3
% Some of the following commands may be useful for the implementation when repeating steps over and over
% for, while, switch, break
