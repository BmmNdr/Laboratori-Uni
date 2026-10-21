import pandas as pd
# Input parameters
df= pd.read_excel("C:/Users/lulli/dataLSP.xlsx")
demand = df['demand'].tolist()
nTime = len(demand)
timeIndex = range(0,nTime)
invInitial = 0 # initial inventory at the beginning
setupCost = df['setup'][0]
holdingCost = df['inventory'][0]
unitCost = df['unit'][0]