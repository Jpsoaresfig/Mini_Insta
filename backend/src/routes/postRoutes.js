const express = require("express");
const router = express.Router();
const multer = require("multer");
const { createPost, getFeed } = require("../controller/postController");
const authMiddleware = require("../middlewares/auth");
const postController = require("../controller/postController");

const upload = multer({ storage: multer.memoryStorage() });

router.post(
  "/",
  authMiddleware,
  upload.single("image"),
  createPost
);

router.get(
  "/feed",
  authMiddleware,
  getFeed
);

router.post(
  "/:postId/like",
  authMiddleware,
  postController.toggleLike
);

module.exports = router;
