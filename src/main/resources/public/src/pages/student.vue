<template>
  <div class="wrapper">
    <button @click="logout">Logout</button>
    <form @submit.prevent="enrollCourse">
      <h3>Add me to course</h3>
      <input v-model="newCourseUID" type="text" placeholder="Enter course UID" />
      <button type="submit">Add Course</button>
    </form>
    Cources: ({{ courses.length }})
    <div>
      <div v-for="course in courses" :key="course.uid">
        {{ course.name }}
        <p>Tasks:</p>
        <ul>
          <li v-for="task in getMyAssignments(course)" :key="task.id">
            <p>{{ task.name }}</p>
            <p>{{ task.description }}</p>
            <button
              @click="completeTask(course.uid, task.id)"
              :disabled="task.finishedAssignees.includes(studentId)"
            >
              Complete task
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const studentId = parseInt(sessionStorage.getItem('studentNumber') ?? '0')

const router = useRouter()

const newCourseUID = ref('')
const courses = ref<Course[]>([])

const refreshCourses = () => {
  fetch('http://localhost:8080/iAmEnrolledIn', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      id: studentId
    })
  })
    .then((res) => res.json())
    .then((data) => (courses.value = data))
}

if (!studentId || isNaN(studentId)) {
  router.push('/')
} else {
  refreshCourses()
}

const enrollCourse = () => {
  fetch('http://localhost:8080/enrollCourse', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      student_id: studentId,
      course_id: newCourseUID.value
    })
  }).then(() => refreshCourses())
}

const getMyAssignments = (course: Course) => {
  return course.assignments.filter((task) => task.assignees.includes(studentId))
}

const completeTask = (courseUID: string, taskID: number) => {
  fetch('http://localhost:8080/completeTask', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      student_id: studentId,
      course_uid: courseUID,
      task_id: taskID
    })
  }).then(() => refreshCourses())
}

const logout = () => {
  sessionStorage.removeItem('teacherNumber')
  router.push('/')
}
</script>
