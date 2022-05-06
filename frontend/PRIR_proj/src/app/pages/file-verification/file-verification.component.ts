import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileService } from 'src/app/files/file.service';
import { ComparisonResult } from 'src/app/models/comparison-result';

@Component({
  selector: 'app-file-verification',
  templateUrl: './file-verification.component.html',
  styleUrls: ['./file-verification.component.css']
})
export class FileVerificationComponent implements OnInit {

  fileId: string;
  results: ComparisonResult[];

  constructor(private route: ActivatedRoute, private fileService: FileService) {    
      this.fileId = this.route.snapshot.paramMap.get('fileId');
  }

  ngOnInit(): void {
  }

  verifyClicked() {
    this.fileService.verifyFile(this.fileId).subscribe(
      resp => this.results = resp
    );
  }
}
