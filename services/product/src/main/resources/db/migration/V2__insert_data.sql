-- Insert Categories
INSERT INTO category (id, name, description) VALUES
(1,  'Electronics',    'All electronic items and gadgets'),
(2,  'Clothing',       'All clothes and fashion items'),
(3,  'Books',          'All kinds of books and literature'),
(4,  'Sports',         'Sports and fitness equipment'),
(5,  'Home & Kitchen', 'Home appliances and kitchen items'),
(6,  'Beauty & Health','Beauty products and health care'),
(7,  'Toys & Games',   'Toys and games for all ages'),
(8,  'Automotive',     'Car and vehicle accessories'),
(9,  'Food & Grocery', 'Fresh food and grocery items'),
(10, 'Furniture',      'Home and office furniture');

-- Insert Products
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES

-- Electronics (category_id = 1)
(1,  'iPhone 15',           'Apple iPhone 15 128GB',                50,   999.99,  1),
(2,  'Samsung Galaxy S24',  'Samsung Galaxy S24 256GB',             40,   899.99,  1),
(3,  'MacBook Pro M5',      'Apple MacBook Pro M5 24GB 1TB',        15,  1899.99,  1),
(4,  'Dell XPS 15',         'Dell XPS 15 Intel i7 16GB',            20,  1499.99,  1),
(5,  'iPad Pro',            'Apple iPad Pro 12.9 inch',             30,   799.99,  1),
(6,  'Sony Headphones',     'Sony WH-1000XM5 Noise Cancelling',     60,   349.99,  1),
(7,  'Samsung 4K TV',       'Samsung 55 inch 4K QLED TV',           25,   799.99,  1),
(8,  'Canon Camera',        'Canon EOS R50 Mirrorless Camera',      18,  1099.99,  1),
(9,  'Apple Watch',         'Apple Watch Series 9 45mm',            45,   499.99,  1),
(10, 'LG Monitor',          'LG 27 inch 4K UHD Monitor',            35,   399.99,  1),

-- Clothing (category_id = 2)
(11, 'White T-Shirt',       'Premium Cotton White T-Shirt',        100,    19.99,  2),
(12, 'Black Jeans',         'Slim Fit Black Jeans',                 75,    49.99,  2),
(13, 'Hoodie',              'Fleece Pullover Hoodie',               80,    39.99,  2),
(14, 'Running Shoes',       'Nike Air Max Running Shoes',           60,    89.99,  2),
(15, 'Winter Jacket',       'Waterproof Winter Jacket',             40,   129.99,  2),
(16, 'Polo Shirt',          'Classic Fit Polo Shirt',               90,    29.99,  2),
(17, 'Chinos',              'Slim Fit Chino Pants',                 70,    44.99,  2),
(18, 'Sneakers',            'Adidas Ultra Boost Sneakers',          55,    99.99,  2),
(19, 'Dress',               'Casual Summer Dress',                  65,    34.99,  2),
(20, 'Formal Shirt',        'White Formal Business Shirt',          85,    39.99,  2),

-- Books (category_id = 3)
(21, 'Java Programming',    'Complete Java Programming Guide',      30,    39.99,  3),
(22, 'Spring Boot',         'Spring Boot Microservices Book',       25,    44.99,  3),
(23, 'Clean Code',          'Clean Code by Robert Martin',          40,    34.99,  3),
(24, 'System Design',       'System Design Interview Book',         35,    49.99,  3),
(25, 'Docker & Kubernetes', 'Docker and Kubernetes in Practice',    28,    44.99,  3),
(26, 'Data Structures',     'Data Structures and Algorithms',       45,    39.99,  3),
(27, 'Python Crash Course', 'Python Programming for Beginners',     50,    29.99,  3),
(28, 'AWS Certified',       'AWS Solutions Architect Guide',        32,    54.99,  3),
(29, 'React JS',            'Modern React JS Development',          38,    34.99,  3),
(30, 'Microservices',       'Microservices Architecture Guide',     22,    49.99,  3),

-- Sports (category_id = 4)
(31, 'Dumbbells Set',       '10kg Adjustable Dumbbells Set',        40,    59.99,  4),
(32, 'Yoga Mat',            'Anti-slip Premium Yoga Mat',           60,    29.99,  4),
(33, 'Resistance Bands',    'Set of 5 Resistance Bands',            75,    19.99,  4),
(34, 'Jump Rope',           'Speed Jump Rope',                      90,    14.99,  4),
(35, 'Protein Powder',      'Whey Protein Chocolate 2kg',           50,    49.99,  4),
(36, 'Gym Gloves',          'Weight Lifting Gym Gloves',            80,    24.99,  4),
(37, 'Water Bottle',        'BPA Free Sports Water Bottle',        100,    19.99,  4),
(38, 'Treadmill',           'Folding Electric Treadmill',           10,   499.99,  4),
(39, 'Cycling Helmet',      'Road Bike Safety Helmet',              45,    59.99,  4),
(40, 'Badminton Set',       'Badminton Racket and Shuttle Set',     55,    34.99,  4),

-- Home & Kitchen (category_id = 5)
(41, 'Air Fryer',           'Digital Air Fryer 5.5L',               35,    89.99,  5),
(42, 'Coffee Maker',        'Drip Coffee Maker 12 Cup',             40,    49.99,  5),
(43, 'Blender',             'High Speed Professional Blender',      30,    79.99,  5),
(44, 'Microwave',           'Samsung 28L Microwave Oven',           25,   129.99,  5),
(45, 'Vacuum Cleaner',      'Dyson V11 Cordless Vacuum',            20,   399.99,  5),
(46, 'Toaster',             '4 Slice Stainless Steel Toaster',      50,    39.99,  5),
(47, 'Rice Cooker',         '1.8L Digital Rice Cooker',             45,    44.99,  5),
(48, 'Knife Set',           '7 Piece Professional Knife Set',       60,    69.99,  5),
(49, 'Non-stick Pan',       '28cm Non-stick Frying Pan',            70,    34.99,  5),
(50, 'Electric Kettle',     '1.7L Fast Boil Electric Kettle',       80,    29.99,  5),

-- Beauty & Health (category_id = 6)
(51, 'Face Serum',          'Vitamin C Brightening Face Serum',     90,    24.99,  6),
(52, 'Sunscreen SPF50',     'Daily UV Protection Sunscreen',       100,    19.99,  6),
(53, 'Hair Dryer',          'Ionic Hair Dryer 2200W',               55,    49.99,  6),
(54, 'Electric Toothbrush', 'Oral-B Smart Electric Toothbrush',     40,    69.99,  6),
(55, 'Moisturizer',         'Hydrating Daily Moisturizer',          85,    22.99,  6),
(56, 'Perfume',             'Chanel No.5 Eau de Parfum 50ml',       30,   129.99,  6),
(57, 'Shampoo',             'Keratin Repair Shampoo 500ml',         95,    14.99,  6),
(58, 'Face Mask',           'Korean Hydrogel Face Mask Pack 10',    70,    19.99,  6),
(59, 'Nail Polish Set',     '12 Colors Nail Polish Set',            60,    24.99,  6),
(60, 'Blood Pressure',      'Digital Blood Pressure Monitor',       35,    44.99,  6),

-- Toys & Games (category_id = 7)
(61, 'LEGO Set',            'LEGO City Police Station Set',         40,    79.99,  7),
(62, 'PS5 Controller',      'DualSense Wireless Controller',        50,    74.99,  7),
(63, 'Board Game',          'Monopoly Classic Board Game',          65,    29.99,  7),
(64, 'RC Car',              'Remote Control Off-Road Car',          45,    49.99,  7),
(65, 'Rubik Cube',          '3x3 Speed Cube Professional',          80,    14.99,  7),
(66, 'Drone',               'Mini Drone with Camera',               25,    99.99,  7),
(67, 'Action Figure',       'Marvel Avengers Action Figure Set',    55,    34.99,  7),
(68, 'Card Game',           'UNO Card Game Family Pack',            90,     9.99,  7),
(69, 'Puzzle',              '1000 Piece Jigsaw Puzzle',             70,    19.99,  7),
(70, 'Nerf Gun',            'Nerf Elite 2.0 Blaster',               60,    24.99,  7),

-- Automotive (category_id = 8)
(71, 'Car Vacuum',          'Portable Car Vacuum Cleaner',          45,    34.99,  8),
(72, 'Dash Cam',            '4K Dash Camera Front and Rear',        30,    89.99,  8),
(73, 'Car Phone Mount',     'Magnetic Car Phone Holder',            80,    19.99,  8),
(74, 'Jump Starter',        'Portable Car Jump Starter 2000A',      25,    79.99,  8),
(75, 'Car Seat Cover',      'Universal Leather Seat Cover Set',     40,    59.99,  8),
(76, 'Tyre Inflator',       'Digital Portable Tyre Inflator',       55,    44.99,  8),
(77, 'Car Wax',             'Turtle Wax Premium Carnauba Wax',      70,    24.99,  8),
(78, 'GPS Navigator',       'Garmin 7 inch GPS Navigator',          20,   149.99,  8),
(79, 'Car Air Freshener',   'Febreze Car Vent Clip Pack 4',        100,     9.99,  8),
(80, 'Steering Cover',      'Leather Steering Wheel Cover',         65,    19.99,  8),

-- Food & Grocery (category_id = 9)
(81, 'Olive Oil',           'Extra Virgin Olive Oil 1L',            90,    14.99,  9),
(82, 'Honey',               'Pure Raw Organic Honey 500g',          85,    12.99,  9),
(83, 'Green Tea',           'Japanese Matcha Green Tea 100g',       75,    19.99,  9),
(84, 'Protein Bar',         'Chocolate Protein Bar Pack 12',        80,    24.99,  9),
(85, 'Almond Nuts',         'Raw Almonds 1kg',                      70,    17.99,  9),
(86, 'Coffee Beans',        'Arabica Coffee Beans 500g',            60,    16.99,  9),
(87, 'Dark Chocolate',      '70% Dark Chocolate Bar Pack 5',        95,    12.99,  9),
(88, 'Oats',                'Rolled Oats Gluten Free 1kg',          85,     8.99,  9),
(89, 'Apple Cider Vinegar', 'Organic Apple Cider Vinegar 500ml',    70,    11.99,  9),
(90, 'Quinoa',              'Organic White Quinoa 1kg',             65,    14.99,  9),

-- Furniture (category_id = 10)
(91,  'Office Chair',       'Ergonomic Mesh Office Chair',          20,   249.99, 10),
(92,  'Standing Desk',      'Electric Height Adjustable Desk',      15,   399.99, 10),
(93,  'Bookshelf',          '5 Tier Wooden Bookshelf',              25,   129.99, 10),
(94,  'Sofa',               '3 Seater Fabric Sofa Grey',            10,   599.99, 10),
(95,  'Coffee Table',       'Modern Glass Coffee Table',            18,   179.99, 10),
(96,  'Bed Frame',          'King Size Wooden Bed Frame',           12,   449.99, 10),
(97,  'Wardrobe',           '3 Door Sliding Mirror Wardrobe',        8,   549.99, 10),
(98,  'Dining Table',       '6 Seater Solid Wood Dining Table',     10,   499.99, 10),
(99,  'TV Stand',           'Modern TV Stand with Storage',         22,   159.99, 10),
(100, 'Nightstand',         'Bedside Table with Drawer',            30,    89.99, 10);

-- Reset sequences after manual inserts
ALTER SEQUENCE category_seq RESTART WITH 51;
ALTER SEQUENCE product_seq RESTART WITH 151;
