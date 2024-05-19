type Task = {
  id: number
  name: string
  description: string
  type: string
  assignees: number[]
  finishedAssignees: number[]
}

type Course = {
  uid: string
  name: string
  students: number[]
  teachers: number[]
  assignments: Task[]
}
