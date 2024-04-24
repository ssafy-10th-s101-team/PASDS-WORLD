import { createRouter, createWebHistory } from 'vue-router'
import DashboardView from '../views/DashboardView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/test',
      name: 'home',
      component: DashboardView
    },
    {
      path: '/',
      name: 'dashboard',
      component: DashboardView,
      redirect: { name: 'DashboardMain' },
      children: [
        {
          path: 'vault',
          name: 'DashboardMain',
          component: () => import('@/components/dashboard/DashboardMain.vue')
        }
        // {
        //   path: 'vault/itemid',
        //   name: 'DashboardInputLoginModal',
        //   component: () => import('@/components/dashboard/DashboardInputLoginModal.vue')
        // }
      ]

      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
    }
  ]
})

export default router
