function [x,z,y,r,beta,iter] = simplex2(A,b,c,beta)

[m,n] = size(A);

iter = 0;
rnymin = -1;
fuzz = sqrt(eps);

fprintf('\n  Iter           zbar    betap      nyq           tmax \n') 
        
ny = [1:n];
ny(beta) = [];

Abeta = A(:,beta);
bbar = Abeta\b;

x(beta,1) = bbar;
x(ny,1) = zeros(length(ny),1);

while rnymin < -fuzz

  Abeta = A(:,beta);
  Any = A(:,ny);

  cbeta = c(beta);
  cny = c(ny);

  y = (Abeta')\cbeta;
  rny = cny - Any' * y;

  zbar = cbeta' * bbar;
  
  if min(bbar) < -fuzz
    fprintf('\n Error, basis is not primal feasible \n\n')
    break;
  end
    
  % Print the values of x_beta, x_ny, y, and r at the start of each iteration
  fprintf('\nIteration: %d\n', iter);
  fprintf('Basic Variables (x_beta):\n');
  for i = 1:length(beta)
    fprintf('  x_%d = %.5f\n', beta(i), bbar(i));
  end
  fprintf('Non-Basic Variables (x_ny):\n');
  for i = 1:length(ny)
    fprintf('  x_%d = 0.00000\n', ny(i));
  end
  fprintf('Dual Variables (y): '); disp(y');
  fprintf('Reduced Costs (r): '); disp(rny');

  % Selecting the entering variable
  if iter == 0
    % First iteration: Choose the second most negative reduced cost
    [sorted_rny, sorted_indices] = sort(rny); % Sort rny in ascending order
    rnymin = sorted_rny(2);                   % Second most negative value
    q = sorted_indices(2);                    % Index of the second most negative
  else
    % Subsequent iterations: Choose the most negative reduced cost
    [rnymin, q] = min(rny);
  end

  if rnymin < -fuzz
    k = ny(q);
    Abark = Abeta\A(:,k);
    Abarkpos = find(Abark > fuzz);
    if length(Abarkpos) > 0
      [tmax, ppos] = min(bbar(Abarkpos) ./ Abark(Abarkpos));
      p = Abarkpos(ppos);
    else
      fprintf('\n Problem has unbounded solution \n\n')
      break;
    end
    
    bbar = bbar - tmax * Abark;
    bbar(p) = tmax;

    % Print updated basic variables after pivot
    fprintf('\nUpdated Basic Variables (x_beta):\n');
    for i = 1:length(beta)
      fprintf('  x_%d = %.5f\n', beta(i), bbar(i));
    end

    betap = beta(p);
    nyq = ny(q);        
    beta(p) = nyq;
    ny(q) = betap;

    str1 = sprintf(' %5g   %12.5e', iter, zbar);
    str2 = sprintf(' %8g   %6g   %12.2e', betap, nyq, tmax);        
    disp([str1 str2])
  
    iter = iter + 1;
    
  else
    str1 = sprintf(' %5g   %12.5e', iter, zbar);
    str2 = sprintf('\n');        
    disp([str1 str2])
  
    fprintf('  Optimal solution found \n\n') 
  end

end

x(beta,1) = bbar;
x(ny,1) = zeros(length(ny),1);

r(beta,1) = zeros(length(beta),1);
r(ny,1) = rny;

z = zbar;

% Print final optimal solution
fprintf('\nFinal Optimal Solution:\n');
fprintf('Basic Variables (x_beta):\n');
for i = 1:length(beta)
  fprintf('  x_%d = %.5f\n', beta(i), bbar(i));
end
fprintf('Non-Basic Variables (x_ny):\n');
for i = 1:length(ny)
  fprintf('  x_%d = 0.00000\n', ny(i));
end
fprintf('Optimal Objective Value (z): %.5e\n', z);

end
