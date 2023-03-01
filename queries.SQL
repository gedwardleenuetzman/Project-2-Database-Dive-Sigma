--see first 25 days
SELECT * FROM sales limit 25;

--see first 25 days of chicken sandwhich, waffle fries and lemonade sales
SELECT day, chicken_sandwich, waffle_fries, lemonade FROM sales limit 25;

--see days 101 to 150 of chicken sandwhich, waffle fries and lemonade sales
SELECT day, chicken_sandwich, waffle_fries, lemonade FROM sales where day>100 and day<151;

--see the first 25 days of usual breakfast item sales
SELECT day, chicken_biscuit, spicy_biscuit, hash_browns FROM sales limit 25;

--see days 51 to 100 of usual breakfast item sales
SELECT day, chicken_biscuit, spicy_biscuit, hash_browns FROM sales where day>50 and day<101;

--see the first 25 days of the ratio of nuggets ordered to the amount of sauces ordered
SELECT day, four_nuggets, eight_nuggets, twelve_nuggets, cfa_sauce, ranch_sauce, honey_mustard FROM sales limit 25;

--see days 101 to 150 of the ratio of nuggets ordered to the amount of sauces ordered
SELECT day, four_nuggets, eight_nuggets, twelve_nuggets, cfa_sauce, ranch_sauce, honey_mustard FROM sales where day>100 and day<151;

--check when most chicken sandwhich sales are
SELECT chicken_sandwich, day FROM sales ORDER BY chicken_sandwich DESC limit 25; 

--see how often waffle fries and lemonades are ordered with chicken sandwiches
SELECT chicken_sandwich, waffle_fries, lemonade FROM sales ORDER BY chicken_sandwich DESC limit 25; 

--see how often waffle fries and lemonades are ordered with chicken sandwiches
SELECT chicken_sandwich, waffle_fries, lemonade FROM sales ORDER BY chicken_sandwich DESC limit 25;

--see how daily how often salads are ordered
SELECT day, cobb_salad, market_salad, side_salad FROM sales limit 25;

--see on days 7 - 14 how often salads are ordered
SELECT day, cobb_salad, market_salad, side_salad FROM sales where day>6 and day<15;

--see first 25 days how treats and deserts are ordered
SELECT day, frosted_lemonade, frosted_coffee, chocolate_cookie FROM sales limit 25;

--see from days 101 to 150 how often treats are sold
SELECT day, frosted_lemonade, frosted_coffee, chocolate_cookie FROM sales where day>100 and day<151;

--see the ratio of sold sandwhiches to sold treats
SELECT chicken_sandwich, frosted_lemonade, frosted_coffee, chocolate_cookie FROM sales ORDER BY chicken_sandwich DESC limit 50;