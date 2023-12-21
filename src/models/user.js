const admin = require("../config/database");
const db = admin.firestore();
const FieldValue = admin.firestore.FieldValue;

const registerUser = async (name, email, hash, gender, age, height, weight, blood_sugar) => {

    const userRecordRef = db.collection('users').doc();

    await userRecordRef.set({
        name: name,
        email: email,
        password: hash,
        token: null,
        gender: gender,
        age: age,
        height: height, 
        weight: weight,
        blood_sugar: blood_sugar,
        createdAt: FieldValue.serverTimestamp(),
        updatedAt: FieldValue.serverTimestamp()
    });

    const userId = userRecordRef.id;

    const newUserRecord = await userRecordRef.get();
    
    return {
        id: userId,
        data: newUserRecord.data()
    };

};

const getUserByEmail = async (email) => {
    
    try {
        const userRecordRef = await db.collection('users').where('email', '==', email).get();
    
        if (userRecordRef.empty) {
            return null; 
        } else {
            const userRecord = userRecordRef.docs[0];
            const userRecordData = userRecord.data();
            userRecordData.id = userRecord.id;

            return userRecordData;
        }
    } catch (error) {
        console.error('Error retrieving user record:', error);
        throw error;    
    }

};

const getUsers = async () => {

    try {
        const users = [];
        const usersSnapshot = await db.collection('users').get();
        usersSnapshot.forEach((doc) => {
            users.push(doc.data());
        });
        return users;
    } catch (error) {
        throw error;
    }

};

const getUserByToken = async (token) => {

    const userRecordRef = await db.collection('users').where('token', '==', token).get();
    
    return userRecordRef;

}


const getUserById = async (userId) => {
    
    const userRecordRef = db.collection('users').doc(userId);
    const userRecord = await userRecordRef.get();

    if(!userRecord.exists) {
        throw new Error('User not found');
    }

    return {
        id: userRecord.id,
        data: userRecord.data()
    };
    
}

const updateProfile = async(userId, gender, weight, height, name, blood_sugar) => {

    try{
        const userRecordRef = db.collection('users').doc(userId);
        await userRecordRef.update({
            gender: gender,
            weight: weight,
            height: height,
            name: name,
            blood_sugar: blood_sugar,
            updatedAt: FieldValue.serverTimestamp()
        });

        const userRecord = await userRecordRef.get();
        return {
            id: userRecord.id,
            data: userRecord.data()
        };

    }catch(error){
        throw error;
    }
        
}

const updateToken = async (userId, token) => {

    try{
        const userRecordRef = db.collection('users').doc(userId);
        await userRecordRef.update({
            token: token
        });
    }catch(error){
        throw error;
    }
}

module.exports = { getUsers, registerUser, getUserByEmail, getUserByToken, getUserById, updateProfile, updateToken};