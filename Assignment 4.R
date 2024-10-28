library(dplyr)

data = read.csv("MSFT.csv", header = TRUE)

###Question 4
##part a
r = log(lead(data$Close)/data$Close)
r_bar = mean(r, na.rm = TRUE)
r_s2 = var(r, na.rm = TRUE)

##part b
sorted = sort(r)
ranks = seq(length(sorted))/(length(sorted) + 1)
z = qnorm(ranks)
theoretical_quantiles = r_bar + z*sqrt(r_s2)

plot(theoretical_quantiles, sorted,
     main = "QQ Plot",
     xlab = "Theoretical Quantiles",
     ylab = "Sample Quantiles")

# Plot y = x line
lines(theoretical_quantiles, theoretical_quantiles, col = "red")
qqnorm(r)
qqline(r)

##part (c) 
# It does not follow a normal distribution since many data points fall outside of y = x line.
# This suggests that many sample quantiles do not follow theoretical quantiles from the normal distribution. 


###Question 5
##part a
n_plus = sum(r > 0, na.rm = TRUE)
p_value = 1 - pbinom(n_plus, size = length(r) - 1, prob = 0.5)

##part b
t = (r_bar - 0)/(sqrt(r_s2/(length(r) - 1)))
