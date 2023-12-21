const express = require('express');
const router = express.Router();
const bodyParser = require('body-parser');
const { getUsersHandler, registerUserHandler, loginHandler, updateProfileHandler, getUserByIdHandler, logoutHandler } = require('../handlers/User');
const { getAllFoodsHandler, getFoodByIdHandler } = require('../handlers/Food');
const { addDailyFoodHandler, getDailyFoodByUserIdHandler, getDailyFoodByDailyDateHandler, getDailyFoodByWeeklyDateHandler, getDailyFoodByDailyMonthHandler } = require('../handlers/DailyFood');
const { verifyToken } = require('../middlewares/Auth');

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

router.post('/login', loginHandler);
router.post('/register', registerUserHandler);
router.get('/users', verifyToken, getUsersHandler);
router.get('/users/:userId', verifyToken, getUserByIdHandler);
router.put('/users/:userId', verifyToken, updateProfileHandler);    
router.delete('/logout', logoutHandler);

router.get('/food', verifyToken, getAllFoodsHandler);
router.get('/food/:foodId', verifyToken, getFoodByIdHandler);

router.post('/daily-food', verifyToken, addDailyFoodHandler);
router.get('/daily-food/:userId', verifyToken, getDailyFoodByUserIdHandler);
router.get('/daily-food-daily/:userId', verifyToken, getDailyFoodByDailyDateHandler);
router.get('/daily-food-weekly/:userId', verifyToken, getDailyFoodByWeeklyDateHandler);
router.get('/daily-food-monthly/:userId', verifyToken, getDailyFoodByDailyMonthHandler);


module.exports = router;