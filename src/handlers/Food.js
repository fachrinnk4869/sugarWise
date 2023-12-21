const { getAllFoods, getFoodById } = require("../models/food");


const getAllFoodsHandler = async (req, res) => {
        
    try {
        const foodRecords = await getAllFoods();
        res.status(200).json({message: 'Foods retrieved successfully', data: foodRecords});
    } catch (error) {
        res.status(400).json({message: error.message});
    }
        
};

const getFoodByIdHandler = async (req, res) => {
        
    const { foodId } = req.params;

    try {
        const foodRecord = await getFoodById(foodId);
        res.status(200).json({message: 'Food retrieved successfully', data: foodRecord});
    } catch (error) {
        res.status(400).json({message: error.message});
    }
        
};

module.exports = {getAllFoodsHandler, getFoodByIdHandler };