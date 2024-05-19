<template>
  <div class="wrapper">
    <div>
      <h2>Student ID</h2>
      <form @submit.prevent="submitStudent">
        <input v-model="studentNumber" type="text" placeholder="Enter student ID" />
        <button type="submit">Login</button>
      </form>
    </div>
    <h3>OR</h3>
    <div>
      <h2>Teacher ID</h2>
      <form @submit.prevent="submitTeacher">
        <input v-model="teacherNumber" type="text" placeholder="Enter teacher ID" />
        <button type="submit">Login</button>
      </form>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const studentNumber = ref('')
const teacherNumber = ref('')

const submitStudent = () => {
  sessionStorage.setItem('studentNumber', studentNumber.value)
  router.push(`/student`)
}

const submitTeacher = () => {
  sessionStorage.setItem('teacherNumber', teacherNumber.value)
  router.push(`/teacher`)
}
</script>
<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  flex-direction: row;
  gap: 24px;
}
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}
</style>
