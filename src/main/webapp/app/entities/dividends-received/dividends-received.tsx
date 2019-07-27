import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './dividends-received.reducer';
import { IDividendsReceived } from 'app/shared/model/dividends-received.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDividendsReceivedProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class DividendsReceived extends React.Component<IDividendsReceivedProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { dividendsReceivedList, match } = this.props;
    return (
      <div>
        <h2 id="dividends-received-heading">
          <Translate contentKey="accounTalkApp.dividendsReceived.home.title">Dividends Receiveds</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.dividendsReceived.home.createLabel">Create new Dividends Received</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {dividendsReceivedList && dividendsReceivedList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.dividendsReceived.paymentDate">Payment Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.dividendsReceived.amountReceived">Amount Received</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.dividendsReceived.taxReturn">Tax Return</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.dividendsReceived.shares">Shares</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {dividendsReceivedList.map((dividendsReceived, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${dividendsReceived.id}`} color="link" size="sm">
                        {dividendsReceived.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={dividendsReceived.paymentDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{dividendsReceived.amountReceived}</td>
                    <td>
                      {dividendsReceived.taxReturn ? (
                        <Link to={`tax-return/${dividendsReceived.taxReturn.id}`}>{dividendsReceived.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {dividendsReceived.shares ? (
                        <Link to={`shares/${dividendsReceived.shares.id}`}>{dividendsReceived.shares.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${dividendsReceived.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${dividendsReceived.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${dividendsReceived.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="accounTalkApp.dividendsReceived.home.notFound">No Dividends Receiveds found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ dividendsReceived }: IRootState) => ({
  dividendsReceivedList: dividendsReceived.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DividendsReceived);
