import { Component, OnInit } from '@angular/core';
import { TransportLineService } from '../../services/transport-line.service';
import { Observable } from 'rxjs';
import { ITransportLine } from '../../model/ITransportLine';


@Component({
  selector: 'app-transport-line-list',
  templateUrl: './transport-line-list.component.html',
  styleUrls: ['./transport-line-list.component.css']
})
export class TransportLineListComponent implements OnInit {

  transportLines : ITransportLine[] = [];

  constructor(private transportLineService: TransportLineService) { }

  ngOnInit() {
    this.transportLineService.getTransportLines().subscribe(
      transportLines => this.transportLines = transportLines
    )

    if (this.transportLines.length == 0)
      console.log("asdasdasd");
  }

 

}
