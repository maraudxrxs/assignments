traffic = read.csv("traffic.csv", header = TRUE)

clear = traffic[traffic$weather_main == "Clear",]
rain = traffic[traffic$weather_main == "Rain",]

##Part a
x_bar = mean(clear$traffic_volume)
y_bar = mean(rain$traffic_volume)

##Part b
n = nrow(clear)
m = nrow(rain)

sx_2 = var(clear$traffic_volume)
sy_2 = var(rain$traffic_volume)

sp_2 = ((n-1)*sx_2 + (m-1)*sy_2)/(n+m-2)

z_0_025 = 1.960

lower_bound = x_bar - y_bar - (z_0_025*sqrt(sp_2)*sqrt((1/n) + (1/m)))
higher_bound = x_bar - y_bar + (z_0_025*sqrt(sp_2)*sqrt((1/n) + (1/m)))

confidence_interval = c(lower_bound, higher_bound)

##Part c
r = floor(((sx_2/n + sy_2/m)^2)/((1/(n-1))*(sx_2/n)^2 + (1/(m-1))*(sy_2/m)^2))

###Since r is huge
t_0_025 = 1.960
lower_bound_t = x_bar - y_bar - (t_0_025*(sqrt((sx_2/n) + (sy_2/m))))
higher_bound_t = x_bar - y_bar + (t_0_025*(sqrt((sx_2/n) + (sy_2/m))))

welchs_t_interval = c(lower_bound_t, higher_bound_t)