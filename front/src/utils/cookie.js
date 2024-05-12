// // cookie.js
// const cookieHelper = {
//   generate: function (name, value) {
//     document.cookie = name + '=' + value + '; Path=/;'
//   },
//   get: function (name) {
//     const nameEQ = name + '='
//     const ca = document.cookie.split(';')
//     for (let i = 0; i < ca.length; i++) {
//       let c = ca[i]
//       while (c.charAt(0) === ' ') c = c.substring(1, c.length)
//       if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length)
//     }
//     return null
//   },
//   delete: function (name) {
//     document.cookie = name + '=; Expires=Thu, 01 Jan 1970 00:00:01 GMT; Path=/;'
//   },
//   deleteAll: function () {
//     const cookies = document.cookie.split(';')
//     for (let i = 0; i < cookies.length; i++) {
//       const cookie = cookies[i]
//       const eqPos = cookie.indexOf('=')
//       const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie
//       document.cookie = name + '=; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/;'
//     }
//   }
// }

// export default cookieHelper

const cookieHelper = {
  generate: function (name, value) {
    document.cookie = `${name}=${encodeURIComponent(value)}; Path=/; SameSite=Strict; Secure`
  },
  get: function (name) {
    const nameEQ = `${name}=`
    const ca = document.cookie.split(';')
    for (let i = 0; i < ca.length; i++) {
      let c = ca[i]
      while (c.charAt(0) === ' ') c = c.substring(1)
      if (c.indexOf(nameEQ) === 0) return decodeURIComponent(c.substring(nameEQ.length))
    }
    return null
  },
  delete: function (name) {
    document.cookie = `${name}=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT; SameSite=Strict; Secure`
  },
  deleteAll: function () {
    const cookies = document.cookie.split(';')
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i]
      const eqPos = cookie.indexOf('=')
      const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie.trim()
      document.cookie = `${name}=; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/; SameSite=Strict; Secure`
    }
  }
}

export default cookieHelper
