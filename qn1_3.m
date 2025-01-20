% Step 1: Load data and define the problem
[A, b, c, beta] = hw1data(030614);  % Load problem data using your birth date

% Step 2: Run the modified simplex method with tracking
[x, z, y, r, beta, iter] = simplex2(A, b, c, beta);