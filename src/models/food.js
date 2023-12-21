const admin = require("../config/database");
const db = admin.firestore();


const getAllFoods = async () => {

    const foodRecords = await db.collection('foods').get();
    const foods = [];

    foodRecords.forEach(food => {
        foods.push({
            id: food.id,
            data: food.data()
        });
    });

    return foods;

}   

const getFoodById = async (foodId) => {

    const foodRecordRef = db.collection('foods').doc(foodId);
    const foodRecord = await foodRecordRef.get();

    if(!foodRecord.exists) {
        throw new Error('Food not found');
    }

    return {
        id: foodRecord.id,
        data: foodRecord.data()
    };

}


module.exports = {getAllFoods, getFoodById};