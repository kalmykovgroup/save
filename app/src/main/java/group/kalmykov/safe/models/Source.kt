package group.kalmykov.safe.models

import android.database.Cursor
import group.kalmykov.safe.annotation.db.Column
import group.kalmykov.safe.annotation.db.Table
import group.kalmykov.safe.db.SourceContract

@Table("sources")
class Source : DbModel {

    @Column("id")
    var id: Int = 0

    @Column("host")
    var host: String

    @Column("password")
    var password: String

    @Column("username")
    var username : String?

    @Column("description")
    var description: String?

    constructor(host: String, password: String, username: String? = null, description: String? = null){
        this.host = host
        this.password = password
        this.username = username
        this.description = description
    }

    constructor(cursor: Cursor){

        with(cursor) {

             host = getString(getColumnIndexOrThrow(SourceContract.Column.HOST))
             username = getString(getColumnIndexOrThrow(SourceContract.Column.USERNAME))
             password = getString(getColumnIndexOrThrow(SourceContract.Column.PASSWORD))
             description = getString(getColumnIndexOrThrow(SourceContract.Column.DESCRIPTION))

        }
    }

    companion object {
        fun createInstance(cursor: Cursor): DbModel {
            return Source(cursor)
        }
    }



}


