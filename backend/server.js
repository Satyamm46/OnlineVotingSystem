const express = require("express");
const cors = require("cors");

const app = express();

app.use(cors());
app.use(express.json());

app.use("/api/auth", require("./routes/authRoutes"));
app.use("/api/leaders", require("./routes/leaderRoutes"));

app.listen(5000, () => {
  console.log("Server running on port 5000");
});