const jwt = require("jsonwebtoken");

const verifyToken = (req, res, next) => {
    
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if(!token) {
        return res.status(401).json({ message: 'Unauthorized access' });
    }else{  
        jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
            if(err) {
                return res.status(403).json({ message: 'Forbidden access' });
            }
        
            req.email = user.email;
            req.name = user.name;
            req.userId = user.userId;
            next();
        });
    }
}

module.exports = { verifyToken };