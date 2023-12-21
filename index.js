const express = require('express');
const app = express();
const port = 3000;
const cors = require('cors');
const dotenv = require('dotenv');
dotenv.config();
const cookieParser = require('cookie-parser');
const apiRoutes = require('./src/routes/api');

app.use(cookieParser());
app.use(cors({ origin: 'https://app-3o5np3qfya-uc.a.run.app', credentials: true }));

app.use('/api', apiRoutes);

app.listen(port, () => {
  console.log(`Example app listening on port http://localhost:${port}`)
});