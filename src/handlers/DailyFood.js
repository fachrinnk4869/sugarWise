const { addDailyFood, getDailyFoodByUserId, getDailyFoodByDailyDate, getDailyFoodByWeeklyDate, getDailyFoodByDailyMonth } = require('../models/dailyfood');
const { getFoodById } = require('../models/food');
const { getUserById } = require('../models/user');


const addDailyFoodHandler = async (req, res) => {

    const { userId, foodId, amount } = req.body;

    try {
        const foodRecord = await getFoodById(foodId);
        const userRecord = await getUserById(userId);   
        const userRecordData = userRecord.id;

        const total_calorie = foodRecord.data.calories * amount;
        const total_protein = foodRecord.data.protein * amount;
        const total_sugar = foodRecord.data.sugars * amount;
        const total_fat = foodRecord.data.total_fat * amount;

        const dailyFoodRecord = await addDailyFood(userRecordData, foodId, amount, total_calorie, total_protein, total_sugar, total_fat);
        res.status(200).json({ message: 'Daily Food added successfully', data: dailyFoodRecord });
    } catch (error) {
        res.status(400).json({ message: error.message });
    }

}

const getDailyFoodByUserIdHandler = async (req, res) => {
    
    const { userId } = req.params;


    try {
        const dailyFoodRecord = await getDailyFoodByUserId(userId);
        res.status(200).json({ message: 'Daily Food retrieved successfully', data: dailyFoodRecord });
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
    
};

const getDailyFoodByDailyDateHandler = async (req, res) => {
        
    const { userId } = req.params;

    try {
        const dailyFoodRecord = await getDailyFoodByDailyDate(userId);
        res.status(200).json({ message: 'Daily Food retrieved successfully', data: dailyFoodRecord });
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
    
};

const getDailyFoodByWeeklyDateHandler = async (req, res) => {
    
    const { userId } = req.params;

    try {
        const dailyFoodRecord = await getDailyFoodByWeeklyDate(userId);
        res.status(200).json({ message: 'Daily Food retrieved successfully', data: dailyFoodRecord });
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
    
};

const getDailyFoodByDailyMonthHandler = async (req, res) => {
        
    const { userId } = req.params;

    try {
        const dailyFoodRecord = await getDailyFoodByDailyMonth(userId);
        res.status(200).json({ message: 'Daily Food retrieved successfully', data: dailyFoodRecord });
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
        
};


module.exports = { addDailyFoodHandler, getDailyFoodByUserIdHandler, getDailyFoodByDailyDateHandler, getDailyFoodByWeeklyDateHandler, getDailyFoodByDailyMonthHandler };