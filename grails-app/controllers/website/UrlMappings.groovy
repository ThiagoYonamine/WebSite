package website

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/index"(view:"/index")
        "/"(view:"/login")
        "/usrPro"(view:"/usrPro")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
