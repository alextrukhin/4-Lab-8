import { createWebHistory, createRouter } from 'vue-router'

import IndexPage from './pages/index.vue'
import StudentPage from './pages/student.vue'
import TeacherPage from './pages/teacher.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: IndexPage
    },
    {
      path: '/student',
      name: 'student',
      component: StudentPage
    },
    {
      path: '/teacher',
      name: 'teacher',
      component: TeacherPage
    }
  ]
})

export default router
