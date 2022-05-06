import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { ComparisonResult } from '../models/comparison-result';
import { FileInfo, FileInfo as FileUploadRequest } from '../models/file-request';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) { }

  uploadFile(formData: FormData): Observable<string> {
    return this.http.post(
      environment.APIEndpoint + '/addFile',
      formData,
      { responseType: 'text' as 'json' }
    ) as Observable<string>;
  }

  updateFileInfo(fileInfo: FileInfo) {
    return this.http.put(      
      environment.APIEndpoint + '/updateFileInfo',
      fileInfo
    );
  }

  verifyFile(fileId: string): Observable<ComparisonResult[]> {
    return this.http.get(
      environment.APIEndpoint + '/compareToAll/' + fileId
    ) as Observable<ComparisonResult[]>;
  }
}
