import numpy as np
import pandas as pd
import time 

def minEig(A,x0,tol,solver):
    
    maxIter = 100
    x = x0
    eigenvalue = None
    R = None
    start_time = time.time()

    for i in range(maxIter):
        # define the right-hand-side vector b
        b = x/np.linalg.norm(x)
        b = b.reshape(-1,1)
        # Solve the linear system Ax=b according to the 'solver' choice
        if solver == "GaussianElmntSPP":
            x = GaussianElmntSPP(A,b)
        elif solver == "Cholesky":
            new_y = None
            if i == 0:
                R = Cholesky(A)
            new_y = forwardSubst(R, b)
            x = backSubst(R.T, new_y)
        else:
            old_x = None
            w = 1.5
            if i == 0:
                old_x = np.zeros((cols,1))
            else:
                old_x = x
            x = SOR(A,b,w,old_x,0.01)

        # Update the eigenvalue lambda
        b_transpose = b.T
        eigenvalue = np.dot(b_transpose, x)

        # Print out the eigenvalue and error in each iteration to help debugging
        print(f'Iteration {i}, Eigenvalue: {1/eigenvalue[0][0]}, Error: {np.max(np.dot(A,x) - x/eigenvalue)}')

        # Check if the termination condition is satisfied
        if (np.max(np.dot(A,x) - x/eigenvalue) < tol):
            break

    runtime = time.time() - start_time
    eig = 1/eigenvalue[0][0]
    print(f'Solver: {solver}, Eigenvalue: {eig}, Iterations: {i+1}, Runtime: {runtime}')
    return eig, runtime

##solver functions

##Cholesky

def Cholesky(A0):
    
    n = len(A0)
    R = np.zeros((n,n))
    A = np.copy(A0)
    for k in range(n):
        if (A[k][k] < 0):
            print('A is not positive definite!')
            return
        R[k][k] = np.sqrt(A[k][k])
        uT = np.array([A[k][k+1:n+1]/R[k][k]])
        R[k][k+1:n+1] = uT
        A[k+1:n+1,k+1:n+1] -= np.matmul(uT.T,uT)
    return R

def backSubst(R,y):   
    n = len(y)
    x = np.zeros((n,1))
    x[n-1] = y[n-1]/R[n-1][n-1]
    for i in range(n-2,-1,-1):
        x[i] = y[i]
        for j in range(i+1,n):
            x[i] -= R[i][j]*x[j]
        x[i] = x[i]/R[i][i]    
    return x

def forwardSubst(R,b):   
    n = len(b)
    y = np.zeros((n,1))
    y[0] = b[0]/R[0][0]
    for j in range(1,n):
        y[j] = b[j]
        for i in range(j):
            y[j] -= R[i][j]*y[i]
        y[j] = y[j]/R[j][j]
    return y

##GaussianElmntSPP

def GaussianElmntSPP(A,b):
    
    m = len(A)
    n = len(A[0])
    if (m!=n):
        print('A is singular!\n')
    
    s = np.zeros((n,1))
    for i in range(n):
        s[i] = abs(A[i][1])
        for j in range(1,n):
            if (abs(A[i][j]) > s[i]):
                s[i] = abs(A[i][j])
        if (s[i]==0):
            print('A is singular!\n')
            return
    
    r = np.arange(n)    
    x = np.zeros((n,1))
    B = np.concatenate((A,b),axis=1)
    
    for i in range(n-1):
        l = i
        maximum = abs(B[r[i]][i])/s[r[i]]
        for j in range(i+1,n):
            ratio = abs(B[r[j]][i])/s[r[j]]
            if (ratio > maximum):
                l = j
                maximum = ratio
                
        if (B[r[l]][i]==0):
            print('A is singular!\n')
            return
        elif (l!=i):
            t = r[l]
            r[l] = r[i]
            r[i] = t
        
        for j in range(i+1,n):
            mul = B[r[j]][i]/B[r[i]][i]
            for k in range(i+1,n+1):
                B[r[j]][k] -= mul*B[r[i]][k]
    
    x[n-1] = B[r[n-1]][n]/B[r[n-1]][n-1]
    for i in range(n-2,-1,-1):
        x[i] = B[r[i]][n]
        for j in range(i+1,n):
            x[i] -= B[r[i]][j]*x[j]
        x[i] = x[i]/B[r[i]][i]
    
    return x

##SOR

def SOR(A,b,omega,x0,tol):
    
    x = x0
    xold = np.copy(x0)
    n = len(b)
    while np.linalg.norm(np.dot(A, x) - b) > tol:
        for i in range(n):
            x[i] = b[i]
            for j in range(i+1,n):
                x[i] -= A[i][j]*x[j]
            for j in range(i):
                x[i] -= A[i][j]*x[j]
            x[i] = (1-omega)*xold[i] + omega*x[i]/A[i][i]
            xold[i] = x[i]
        
    return x

path = "C:/Users/parma/OneDrive/Documents/y2s2/dsa2102/matrixA.csv"
df = pd.read_csv('matrixA.csv', header = None)

matrix = df.to_numpy()
TOL = 0.005
rows, cols = matrix.shape
x_0 = np.ones(cols)

##Question 3 part a
print(minEig(matrix, x_0, TOL, GaussianElmntSPP))

##Question 3 part b
print(minEig(matrix, x_0, TOL, Cholesky))

##Question 3 part c
print(minEig(matrix, x_0, TOL, SOR))
