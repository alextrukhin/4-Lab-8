<template>
  <div class="wrapper">
    <button @click="logout">Logout</button>
    <form @submit.prevent="enrollCourse">
      <h3>Add to course</h3>
      <input v-model="newCourseUID" type="text" placeholder="Enter course UID" />
      <button type="submit">Add Course</button>
    </form>
    <form @submit.prevent="createCourse">
      <h3>Create course</h3>
      <input v-model="newCourseForm.name" type="text" placeholder="Enter name" />
      <button type="submit">Create course</button>
    </form>
    <h3>Cources: ({{ courses.length }})</h3>
    <div>
      <div v-for="course in courses" :key="course.uid">
        <p>{{ course.name }}</p>
        <code>{{ course.uid }}</code>
        <p>Students:</p>
        <ul>
          <li v-for="student in course.students" :key="student">
            {{ student }}
          </li>
        </ul>
        <p>Tasks:</p>
        <ul>
          <li v-for="task in course.assignments" :key="task.id">
            <p>{{ task.name }}</p>
            <p>{{ task.description }}</p>
            <p>Assignees:</p>
            <ul>
              <li v-for="assignee in task.assignees" :key="assignee">
                {{ assignee }}
              </li>
            </ul>
            <p>Completed task:</p>
            <ul>
              <li v-for="assignee in task.finishedAssignees" :key="assignee">
                {{ assignee }}
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
    <form @submit.prevent="createtask">
      <h3>New task</h3>
      Course
      <select v-model="newTaskForm.courseUID">
        <option v-for="course in courses" :key="course.uid" :value="course.uid">
          {{ course.name }}
        </option>
      </select>
      <input v-model="newTaskForm.name" type="text" placeholder="Enter name" />
      <textarea v-model="newTaskForm.description" placeholder="Enter description"></textarea>
      <select v-model="newTaskForm.assignees" multiple placeholder="Choose assignees">
        <option v-for="s in courseStudents" :key="s" :value="s">
          {{ s }}
        </option>
      </select>
      <button>Create task</button>
    </form>
  </div>
</template>
<script setup lang="ts">
import { computed, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const newCourseUID = ref('')
const courses = ref<Course[]>([])
const newCourseForm = reactive<{
  name: string
}>({
  name: ''
})
const newTaskForm = reactive<{
  courseUID: string
  name: string
  description: string
  assignees: number[]
}>({
  courseUID: '',
  name: '',
  description: '',
  assignees: []
})
const courseStudents = computed(() => {
  const course = courses.value.find((course) => course.uid === newTaskForm.courseUID)
  return course?.students ?? []
})

const refreshCourses = () => {
  fetch('http://localhost:8080/iAmTeaching', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      id: parseInt(sessionStorage.getItem('teacherNumber')!)
    })
  })
    .then((res) => res.json())
    .then((data) => (courses.value = data))
}

if (!sessionStorage.getItem('teacherNumber')) {
  router.push('/')
} else {
  refreshCourses()
}

const createCourse = () => {
  fetch('http://localhost:8080/createCourse', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      teacher_id: parseInt(sessionStorage.getItem('teacherNumber')!),
      name: newCourseForm.name
    })
  })
    .then((res) => res.json())
    .then(() => refreshCourses())
}

const enrollCourse = () => {
  fetch('http://localhost:8080/teachCourse', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      teacher_id: parseInt(sessionStorage.getItem('teacherNumber')!),
      course_id: newCourseUID.value
    })
  }).then(() => refreshCourses())
}

const createtask = () => {
  fetch('http://localhost:8080/createTask', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      type: 'assignment',
      teacher_id: parseInt(sessionStorage.getItem('teacherNumber')!),
      course_id: newTaskForm.courseUID,
      name: newTaskForm.name,
      description: newTaskForm.description,
      assignees: newTaskForm.assignees
    })
  }).then(() => refreshCourses())
}

const logout = () => {
  sessionStorage.removeItem('teacherNumber')
  router.push('/')
}
</script>
