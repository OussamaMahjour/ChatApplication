type User={
    username:string;
    firstname?:string;
    lastname?:string;
    about?:string;
    phone?:number;
    email?:string;
    profilePicture?:string;
    gender?:Gender;
    birthday?:Date;
    createdAt?:Date;
    updatedAt?:Date;
    scheduledToBeDeletedAt?:Date;
}

export enum Gender{
    MALE = 'MALE',
    FEMALE = 'FEMALE'
}


export default User