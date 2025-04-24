const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const clubRoutes = require("./routes/clubRoutes");
const eurekaHelper = require("./eureka-helper");
const path = require("path");
const cors = require("cors");

const app = express();
const PORT = process.env.PORT || 3000;

// Enable CORS for all routes
app.use(cors({
  origin: ["http://localhost:4200", "http://localhost:8082"],
  methods: ["GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"],
  allowedHeaders: ["Authorization", "Content-Type", "X-Requested-With"],
  exposedHeaders: ["Authorization"],
  credentials: true
}));

app.use(bodyParser.json());

// Connect to MongoDB Atlas (replace 'YOUR_CONNECTION_STRING' with your actual MongoDB Atlas connection string)
mongoose.connect(
  "mongodb+srv://root:root@cluster0.wa1te.mongodb.net/clubMS?retryWrites=true&w=majority",
  {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  }
);

app.use(
  "/upload-directory",
  express.static(path.join(process.cwd(), "upload-directory"))
);
app.use("/clubs", clubRoutes);

// Add health check endpoint for Eureka
app.get('/health', (req, res) => {
  res.status(200).json({ status: 'UP' });
});

// Register with Eureka
// const eurekaHost =
//   process.env.EUREKA_CLIENT_SERVICEURL_DEFAULTZONE || "localhost";
// const eurekaClient = new Eureka({
//   instance: {
//     app: "club-node",
//     hostName: "localhost", // Change this if needed
//     ipAddr: "127.0.0.1", // Change this if needed
//     port: {
//       $: PORT,
//       "@enabled": "true",
//     },
//     vipAddress: "club-ms",
//     dataCenterInfo: {
//       "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
//       name: "MyOwn",
//     },
//   },
//   eureka: {
//     host: eurekaHost, // Eureka server host
//     port: 8761, // Eureka server port
//     servicePath: "/eureka/apps/",
//   },
// });

// eurekaClient.start();

// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});

eurekaHelper.registerWithEureka("club-MS", PORT);
