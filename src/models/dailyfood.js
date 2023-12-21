const admin = require("../config/database");
const db = admin.firestore();


const FieldValue = admin.firestore.FieldValue;

const addDailyFood = async (userId, foodId, amount, total_calorie, total_protein, total_sugar, total_fat) => {

    const dailyFoodRecordRef = db.collection('dailyfoods').doc();

    await dailyFoodRecordRef.set({
        userId: userId,
        foodId: foodId,
        date: FieldValue.serverTimestamp(),
        amount: amount,
        total_calorie: total_calorie,
        total_protein: total_protein,
        total_sugar: total_sugar,
        total_fat: total_fat,
    });

    const dailyFoodId = dailyFoodRecordRef.id;

    const newDailyFoodRecord = await dailyFoodRecordRef.get();
    
    return {
        id: dailyFoodId,
        data: newDailyFoodRecord.data()
    };

}

const getDailyFoodByUserId = async (userId) => {

    const dailyFoodRecordRef = await db.collection('dailyfoods').where('userId', '==', userId).get();

    if(dailyFoodRecordRef.empty) {
        throw new Error('Daily Food not found');
    }

    const dailyFood = dailyFoodRecordRef.docs.map( async (doc) => {
        
        const foodId = doc.data().foodId;
        const foodSnapshot = await db.collection('foods').doc(foodId).get();
        const food = foodSnapshot.data();

        return {
            id_dailyfood: doc.id,
            data: {
                ...doc.data(),
                id_food: foodId,
                food_name : food.food_name,
                image_url: food.image_url
            
            }
        }
    });

    return Promise.all(dailyFood);

}


const getDailyFoodByDailyDate = async (userId) => {
    
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    try {
        const dailyFoodRecordRef = await db.collection('dailyfoods')
        .where('userId', '==', userId)
        .where('date', '>=', today)
        .where('date', '<=', new Date(today.getTime() + 24 * 60 * 60 * 1000))
        .get();

        if (dailyFoodRecordRef.empty) {
            throw new Error('Daily Food not found');
        }

        const dailyFood = dailyFoodRecordRef.docs.map(async (doc) => {
            const foodId = doc.data().foodId;
            const foodSnapshot = await db.collection('foods').doc(foodId).get();
            const food = foodSnapshot.data();

            return {
                id_dailyfood: doc.id,
                data: {
                    ...doc.data(),
                    id_food: foodId,
                    food_name: food.food_name,
                    image_url: food.image_url
                }
            }
        });

        return Promise.all(dailyFood);

    } catch (error) {
        console.error('Error:', error.message);
        throw error; 
    }
}





const getDailyFoodByWeeklyDate = async (userId) => {

    const startOfWeek = new Date();
    startOfWeek.setHours(0,0,0,0);

    const endOfWeek = new Date();
    endOfWeek.setHours(23,59,59,999);

    const dailyFoodRecordRef = await db.collection('dailyfoods').where('userId', '==', userId).where('date', '>=', startOfWeek).where('date', '<=', endOfWeek).get();

    if(dailyFoodRecordRef.empty) {
        throw new Error('Daily Food not found');
    }

    const dailyFood = dailyFoodRecordRef.docs.map( async (doc) => {
            
        const foodId = doc.data().foodId;
        const foodSnapshot = await db.collection('foods').doc(foodId).get();
        const food = foodSnapshot.data();

        return {
            id_dailyfood: doc.id,
            data: {
                ...doc.data(),
                id_food: foodId,
                food_name : food.food_name,
                image_url: food.image_url
            
            }
        }
    });

    return Promise.all(dailyFood);
}

const getDailyFoodByDailyMonth = async (userId) => {
    
    const today = new Date();
    const firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);
    const lastDayOfMonth = new Date(today.getFullYear(), today.getMonth() + 1, 0);
    
    
    const dailyFoodRecordRef = await db.collection('dailyfoods').where('userId', '==', userId).where('date', '>=', firstDayOfMonth).where('date', '<=', lastDayOfMonth).get();

    if(dailyFoodRecordRef.empty) {
        throw new Error('Daily Food not found');
    }

    const dailyFood = dailyFoodRecordRef.docs.map( async (doc) => {
            
        const foodId = doc.data().foodId;
        const foodSnapshot = await db.collection('foods').doc(foodId).get();
        const food = foodSnapshot.data();

        return {
            id_dailyfood: doc.id,
            data: {
                ...doc.data(),
                id_food: foodId,
                food_name : food.food_name,
                image_url: food.image_url
            
            }
        }
    });

    return Promise.all(dailyFood);
}

module.exports = { addDailyFood, getDailyFoodByUserId, getDailyFoodByWeeklyDate, getDailyFoodByDailyDate, getDailyFoodByDailyMonth};