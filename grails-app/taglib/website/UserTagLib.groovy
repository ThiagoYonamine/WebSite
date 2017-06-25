package website

class UserTagLib {

        def emoticon = { attrs, body ->
            out << body() << (attrs.happy == 'true' ? " :-)" : " :-(")
        }

        def like ={attrs, body ->
            if(attrs.categoria=="parques"){
                out  << attrs.user.parques+1
            }
            if(attrs.categoria=="museus"){
                out  << attrs.user.museus+1
            }

        }
}

