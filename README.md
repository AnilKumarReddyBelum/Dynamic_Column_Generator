# Dynamic_Column_Generator
Dynamically generate a column for the given schema and table and also perform insertion on generated column


#API's



#API : Adding column to a table dynamically

URL:
------------------------------
http://localhost:8080/config

PAYLOAD:
-------------------------------------
{
	"entityName": "product",
	"columnName": "details",
	"columnType": "varchar"
}



#API : Insert some data into dynamically added column

URL:
------------------------------
http://localhost:8080/config/insert

PAYLOAD:
-------------------------------------
{
	"tableName": "product",
	"schemaName": "public",
	"entityData": {"id": 12321, "name":"Product Number 7", "details": "Tetsing"}
}

URL:
------------------------------
http://localhost:8080/config/update

PAYLOAD:
-------------------------------------
{
	"tableName": "product",
	"schemaName": "public",
	"entityData": {"id": 33, "name":"Product Number 7-33", "details": "Tetsing33"}
}



#NOTE

Id will ge Generated automatically, so we are neglecting the id, even if you supply




#Demo

![dynamic-column-generator](https://user-images.githubusercontent.com/11975625/144183265-eb1f247b-c2e1-42ad-842c-89e9e82b0b5d.gif)
![dynamic-column-generator-update](https://user-images.githubusercontent.com/11975625/144212200-f8b369b0-682f-4dae-bac4-f072d1c0ca54.gif)




#Autor:
---------


