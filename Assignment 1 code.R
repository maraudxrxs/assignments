#setwd("C:/Users/parma/OneDrive/Documents/y2s2/st2132/Assignment1_ST2132_2024")
GMM = read.csv("GMM.csv", header = TRUE)
K_1 = GMM[GMM$K == 1,]
K_0 = GMM[GMM$K == 0,]

pi_0 = nrow(K_0)/(nrow(K_0) - nrow(K_1))

μ_0 = (mean(K_0$X))

μ_1 = (mean(K_1$X))

sd_0_squared = sum((K_0$X - μ_0)^2)/(nrow(K_0))
                                     
sd_1_squared = sum((K_1$X - μ_1)^2)/(nrow(K_1))
                                     
                                     