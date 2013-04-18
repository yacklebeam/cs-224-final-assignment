"""
Project BitCal
data intial parser
Output: Text file with format [food, Kcal]
"""
def main():
    d = []
    final = {}
    file = open("data.csv",'r')
    count = 0
    name_start = False
    
    for line in  file:
        temp = line.split(",")
        if count == 0:
            print temp
        else:
            if "\"" in temp[1]:
                name_start = True
                temp_count = 1
                name = temp[1]
            while name_start == True:
                temp_count = temp_count + 1
                name = name + " " + temp[temp_count]
                if "\"" in temp[temp_count]:
                    name_start = False
                else:
                    name_start = True
            temp_string = name + ","
            temp_string = temp_string + temp[temp_count + 1]
            line_array = temp_string.split(",")
            d.append(line_array)
        count = count + 1
    file.close()
    for i in d:
        temp = i
        name = temp[0]
        cal = temp[1]
        final[name] = cal

    file = open("data.dat",'w+')

    count = 0
    values = final.values()
    keys = final.keys()
    for i in keys:
        out_str = keys[count]+ ":" + values[count] + "\n"
        file.write(out_str)
        print "writing line: "
        count = count + 1
        print count
    file.close

main()
                
        
        
        
