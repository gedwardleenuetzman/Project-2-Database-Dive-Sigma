import random

# this is a comment to test if my ssh authentication is working on my new laptop - Garrison
# test 2 - new branch for feature: Restock Report

# Generate random SQL insert statements with sequential order IDs and write to file
num_inserts = 50
order_id = 1

with open("inserts.sql", "w") as f:
    for i in range(num_inserts):
        num_products = random.randint(1, 5)
        products = random.sample(range(0, 26), num_products)
        quantities = [random.randint(1, 10) for _ in range(num_products)]

        sql_values = []
        for j in range(num_products):
            product_id = products[j]
            quantity = quantities[j]
            sql_values.append(f"({order_id}, {product_id}, {quantity})")

        sql_insert = f"INSERT INTO order_products (order_id, prod_id, quantity) VALUES {','.join(sql_values)};"
        f.write(sql_insert + "\n")
        
        order_id += 1

num_inserts = 1000

days_per_order = 1

if num_inserts > 365:
    days_per_order = num_inserts // 365 + 1

with open("inserts2.sql", "w") as f:
    sql_inserts = []
    for i in range(num_inserts):
        id = i + 1
        totalprice = round(random.uniform(1.55, 40), 2)
        day = i // days_per_order + 1
        sql_insert = f"({id}, {totalprice:.2f}, {day})"
        sql_inserts.append(sql_insert)
    f.write("INSERT INTO orders (id, totalprice, day) VALUES " + ", ".join(sql_inserts) + ";")