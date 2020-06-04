import pandas as pd
import mysql.connector
import math 

# Import CSV
countrypath = ['./greece.csv', './usa.csv', './fra.csv', './italia.csv', './arg.csv', './china.csv', './canada.csv', './japan.csv', './canada.csv', './uk.csv']
data = []
df = []
for path in countrypath:
    data = pd.read_csv (path, skiprows = 4)  

    x = data.head(0)
    col = []
    for y in x:  
        col.append(str(y))
        
    df.append(pd.DataFrame(data,columns=col))


conn = mysql.connector.connect(host="localhost",user="root",passwd="chrisGR1995")
cursor = conn.cursor()
cursor.execute('DROP SCHEMA IF EXISTS countriesdata')
cursor.execute('CREATE SCHEMA `countriesdata` ')
conn = mysql.connector.connect(host="localhost",user="root",passwd="chrisGR1995",database="countriesdata")
cursor = conn.cursor()

# Create Table
cursor.execute('''CREATE TABLE `countries` (
  `idId` int(11) NOT NULL,
  `country_name` varchar(45) NOT NULL,
  `country_code` varchar(45) NOT NULL,
  `indicator_name` varchar(300) NOT NULL,
  `indicator_code` varchar(45) NOT NULL,
  PRIMARY KEY (`idId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci''')

cursor.execute('''CREATE TABLE `indicators` (
  `id` int(11) NOT NULL,
  `years` varchar(45) NOT NULL,
  `year_percentage` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`years`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `countries` (`idId`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci''')


i  = 1
mult = 0
for dfpointer in df:
    mult += 1
    line_in_file = 0
    for f in dfpointer.itertuples():
        line_in_file += 1
        if line_in_file in [1,2,3,4,5,6,8,9,10,11,13,14,15,16,17,18,19,20,22,23,24,25,26,27,28,30,31,32,33,34,35,37,38,39,40,41,42,44,45,46,47,48]:
            continue
        x1 = f._1
        x2 = f._2
        x3 = f._3
        x4 = f._4
        cursor.execute('''INSERT INTO countries (idId, country_name, country_code, indicator_name, indicator_code) VALUES (%s,%s,%s,%s,%s)''', (i, x1,x2,x3,x4,))
        
        for z in range(5,64):       
            j = ''.join(["f._",str(z)])    
            if math.isnan(eval(j)):
                j = '0'
            
            cursor.execute('''INSERT INTO indicators (id, years, year_percentage) VALUES (%s,%s,%s)''', (i, col[z-1],eval(j),))
    
        if i == 10*mult:
            i+=1
            break
        i+=1

conn.commit()
