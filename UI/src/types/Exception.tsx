
class Exception extends Error {
    error:string;
    type:ExceptionType;
    constructor(error:string,type:ExceptionType){
        super(error)
        this.error=error
        this.type=type
        this.name = "Exception";
        Object.setPrototypeOf(this, Exception.prototype);
    }
    
    
}

export enum ExceptionType {
    BAD_CREDENTIAL="BAD_CREDENTIAL",
    PATH_NOT_FOUND="PATH_NOT_FOUND",
    METHOD_NOT_ALLOWED="METHOD_NOT_ALLOWED",
    UNKNOWN="UNKNOWN"
}


export default Exception

