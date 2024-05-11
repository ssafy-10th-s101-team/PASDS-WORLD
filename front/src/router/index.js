import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import MemberView from '@/views/MemberView.vue'
import HomeView from '@/views/HomeView.vue'
import OrganizationView from '@/views/OrganizationView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/member',
      name: 'member',
      component: MemberView,
      redirect: { name: 'memberLogin' },
      children: [
        {
          path: 'login',
          name: 'memberLogin',
          component: () => import('@/components/member/MemberLogin.vue')
        },
        {
          path: 'signup',
          name: 'memberSignup',
          component: () => import('@/components/member/MemberSignup.vue')
        },
        {
          path: 'forgot-password',
          name: 'memberForgotPassword',
          component: () => import('@/components/member/MemberForgotPassword.vue')
        },
        {
          path: 'signup2',
          name: 'memberSignup2',
          component: () => import('@/components/member/MemberSignup2.vue')
        },
        {
          path: 'signup3',
          name: 'memberSignup3',
          component: () => import('@/components/member/MemberSignup3.vue')
        },
        {
          path: 'signup4',
          name: 'memberSignup4',
          component: () => import('@/components/member/MemberSignup4.vue')
        },
        {
          path: 'forgot-totp-key',
          name: 'memberForgotTotpKey',
          component: () => import('@/components/member/MemberForgotTotpKey.vue')
        },
        {
          path: 'login2',
          name: 'memberLogin2',
          component: () => import('@/components/member/MemberLogin2.vue')
        },
        {
          path: 'my-page',
          name: 'myPage',
          component: () => import('@/components/member/MemberMyPage.vue')
        }
      ]
    },
    {
      path: '/main',
      name: 'main',
      component: MainView,
      redirect: { name: 'mainpage' },
      children: [
        {
          path: 'vault',
          name: 'mainpage',
          component: () => import('@/components/main/MainMainpage.vue')
        },
        {
          path: 'team-management',
          name: 'teamManagement',
          component: () => import('@/components/main/MainTeamManagement.vue')
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
    },
    {
      path: '/organization',
      name: 'organization',
      component: OrganizationView,
      redirect: { name: 'organizationTeam' },
      children: [
        {
          path: 'team',
          name: 'organizationTeam',
          component: () => import('@/components/organization/OrganizationTeam.vue')
        },
        {
          path: 'member',
          name: 'organizationMember',
          component: () => import('@/components/organization/OrganizationMember.vue')
        },
        {
          path: 'setting',
          name: 'organizationSetting',
          component: () => import('@/components/organization/OrganizationSetting.vue')
        },
        {
          path: 'dashboard',
          name: 'organizationDashboard',
          component: () => import('@/components/organization/OrganizationDashboard.vue')
        },
        {
          path: 'costGraph',
          name: 'costGraph',
          component: () => import('@/components/dashboard/DashboardCostGraph.vue')
        }
      ]
    }
  ]
})

// 로그인 및 회원가입 페이지 이름들을 배열로 정의
const authRelatedRoutes = [
  'memberLogin',
  'memberSignup',
  'memberForgotPassword',
  'memberSignup2',
  'memberSignup3',
  'memberSignup4',
  'memberForgotTotpKey',
  'memberLogin2'
]

const publicRoutes = ['home', ...authRelatedRoutes]

router.beforeEach((to, from, next) => {
  const isAuthenticated = sessionStorage.getItem('nickname')

  if (isAuthenticated) {
    // 로그인한 사용자는 인증 관련 페이지 접근 시 홈으로 리다이렉트
    if (authRelatedRoutes.includes(to.name)) {
      next({ name: 'home' })
    } else {
      next() // 그 외의 경우는 정상적으로 페이지 접근 허용
    }
  } else {
    // 로그인하지 않은 사용자는 publicRoutes에 정의된 페이지만 접근 허용
    if (publicRoutes.includes(to.name)) {
      next()
    } else {
      next({ name: 'memberLogin' }) // 그 외의 페이지는 로그인 페이지로 리다이렉트
    }
  }
})

export default router
