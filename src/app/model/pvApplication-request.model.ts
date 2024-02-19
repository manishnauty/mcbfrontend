import { Borrower } from "./borrower-model";
import { Facilty } from "./facility-model";
import { User } from "./user-model";
import { Document } from "./document-model";
import { Comment } from "./comment-model";


export class pvApplicationRequest{
    fosreferenceNumber!: string;
    type!: string;
    createBy!: User
    facilityDto!: Facilty
    borrowersDto: Borrower[] = [];
    documentsDto: Document[] = [];
    commentsDto: Comment[] = [];
  request: any;
}