##Question 4

data = read.csv("AB_2024.csv", header = TRUE)

A = data[data$landing_page == "A",]
B = data[data$landing_page == "B",]

##(b)
P_A = mean(A$converted)
P_B = mean(B$converted)

##(c) calculate test statistic of hypothesis test
n = nrow(A)
m = nrow(B)

p = (sum(A$converted) + sum(B$converted))/(n+m)
##test statistic
t = ((P_A - P_B)/sqrt(p*(1-p)*((1/n) + (1/m))))

##(d) 
##z0.025 = 1.960 -> t < z0.025, null hypothesis is not rejected

##Question 5

traffic_data = read.csv("traffic.csv", header = TRUE)

X = traffic_data[traffic_data$weather_main == 'Clear', c("traffic_volume", "weather_main")]
Y = traffic_data[traffic_data$weather_main == 'Rain',c("traffic_volume", "weather_main")]


##(b) assume variance for x and y are the same

n = nrow(X)
m = nrow(Y)
mean_x = mean(X$traffic_volume)
mean_y = mean(Y$traffic_volume)

s2_x = sum((X$traffic_volume - mean_x)^2)/(n-1)
s2_y = sum((Y$traffic_volume - mean_y)^2)/(m-1)

s2_p = ((n-1)*s2_x + (m-1)*s2_y)/(n+m-2)

t_b = (mean_x - mean_y)/sqrt(s2_p * ((1/n) + (1/m)))

##(c)
r = floor(((s2_x/n) + (s2_y/m))^2/((1/(n-1))*(s2_x/n)^2 + (1/(m-1))*(s2_y/m)^2))
t_c = (mean_x - mean_y)/sqrt((s2_x/n) + (s2_y/m))

##(d)
t_d = s2_x/s2_y
