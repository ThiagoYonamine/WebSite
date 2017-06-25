package website

class UserTagLib {

        def emoticon = { attrs, body ->
            out << body() << (attrs.happy == 'true' ? " :-)" : " :-(")
        }

}

