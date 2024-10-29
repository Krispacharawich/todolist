// src/store.js
import { configureStore } from "@reduxjs/toolkit";
import tasksSlide from "./tasksSlide";

const store = configureStore({
  reducer: {
    tasksState: tasksSlide,
  },
});
export default store;