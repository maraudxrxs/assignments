library(dplyr)

data = read.csv("MSFT.csv", header = TRUE)

###Question 3
##part a
r = log(lead(data$Close)/data$Close)
r = na.omit(r)
r_bar = mean(r, na.rm = TRUE)
r_s2 = var(r, na.rm = TRUE)

observed_1 = sum(r < -0.001)
observed_2 = sum(r < -0.0004) - observed_1
observed_3 = sum(r < 0) - sum(r < -0.0004)
observed_4 = sum(r < 0.0004) - sum(r < 0)
observed_5 = sum(r < 0.001) - sum(r < 0.0004)
observed_6 = sum(r >= 0.001)

mu <- 0       # Mean
sigma <- 0.02  # Standard deviation

# Calculate the probabilities for each interval
expected_1 <- 9574*pnorm(-0.001, mean = mu, sd = sigma)
expected_2 <- 9574*pnorm(-0.0004, mean = mu, sd = sigma) - 9574*pnorm(-0.001, mean = mu, sd = sigma)
expected_3 <- 9574*pnorm(0, mean = mu, sd = sigma) - 9574*pnorm(-0.0004, mean = mu, sd = sigma)
expected_4 <- 9574*pnorm(0.0004, mean = mu, sd = sigma) - 9574*pnorm(0, mean = mu, sd = sigma)
expected_5 <- 9574*pnorm(0.001, mean = mu, sd = sigma) - 9574*pnorm(0.0004, mean = mu, sd = sigma)
expected_6 <- 9574*(1 - pnorm(0.001, mean = mu, sd = sigma))

test_statistic = ((observed_1 - expected_1)^2/(expected_1)) + ((observed_2 - expected_2)^2/expected_2) + ((observed_3 - expected_3)^2/expected_3) + ((observed_4 - expected_4)^2/expected_4) + ((observed_5 - expected_5)^2/expected_5) + ((observed_6 - expected_6)^2/expected_6)

###part b
mu_1 <- mean(r)
sigma_1 <- sd(r)

e_1 <- 9574*pnorm(-0.001, mean = mu_1, sd = sigma_1)
e_2 <- 9574*pnorm(-0.0004, mean = mu_1, sd = sigma_1) - 9574*pnorm(-0.001, mean = mu_1, sd = sigma_1)
e_3 <- 9574*pnorm(0, mean = mu_1, sd = sigma_1) - 9574*pnorm(-0.0004, mean = mu_1, sd = sigma_1)
e_4 <- 9574*pnorm(0.0004, mean = mu_1, sd = sigma_1) - 9574*pnorm(0, mean = mu_1, sd = sigma_1)
e_5 <- 9574*pnorm(0.001, mean = mu_1, sd = sigma_1) - 9574*pnorm(0.0004, mean = mu_1, sd = sigma_1)
e_6 <- 9574*(1 - pnorm(0.001, mean = mu_1, sd = sigma_1))

new_test_statistic = ((observed_1 - e_1)^2/e_1) + ((observed_2 - e_2)^2/e_2) + ((observed_3 - e_3)^2/e_3) + ((observed_4 - e_4)^2/e_4) + ((observed_5 - e_5)^2/e_5) + ((observed_6 - e_6)^2/e_6)

