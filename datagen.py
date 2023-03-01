import csv
import random

# Define the items for sale
items = ["Chicken_Sandwich", "Spicy_Sandwich", "Deluxe_Sandwich", "Spicy_Deluxe", "Chicken_Biscuit",
         "Spicy_Biscuit", "Hash_Browns", "Four_Nuggets", "Eight_Nuggets", "Twelve_Nuggets", "Cobb_Salad",
         "Market_Salad", "Waffle_Fries", "Side_Salad", "Waffle_Chips", "Frosted_Lemonade", "Frosted_Coffee",
         "Chocolate_Cookie", "Lemonade", "Ice_Tea", "Sweet_Tea", "Water_Bottle", "CFA_Sauce", "Ranch_Sauce",
         "Honey_Mustard"]

# Generate sales data for each day
num_days = 365
sales_data = {}
for item in items:
    sales_data[item] = [random.randint(100, 500) for _ in range(num_days)]

# Generate two special days with larger sales numbers
special_days = []
while len(special_days) < 2:
    special_day = random.randint(14, num_days - 14)  # Pick a random day at least 2 weeks apart from the previous special day
    if all(abs(special_day - prev_special_day) >= 14 for prev_special_day in special_days):
        for item in items:
            sales_data[item][special_day] = random.randint(1000, 2000)
        special_days.append(special_day)

# Write the sales data to a .csv file
with open("sales_data.csv", "w", newline="") as csvfile:
    writer = csv.writer(csvfile)
    header = ["Day"] + items
    writer.writerow(header)
    for day in range(1, num_days + 1):
        row = [day]
        for item in items:
            row.append(sales_data[item][day-1])
        writer.writerow(row)
