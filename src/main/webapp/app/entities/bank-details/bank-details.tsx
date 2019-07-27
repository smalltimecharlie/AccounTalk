import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './bank-details.reducer';
import { IBankDetails } from 'app/shared/model/bank-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBankDetailsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BankDetails extends React.Component<IBankDetailsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { bankDetailsList, match } = this.props;
    return (
      <div>
        <h2 id="bank-details-heading">
          <Translate contentKey="accounTalkApp.bankDetails.home.title">Bank Details</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.bankDetails.home.createLabel">Create new Bank Details</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {bankDetailsList && bankDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.accountHolderName">Account Holder Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.accountNumber">Account Number</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.sortCode">Sort Code</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.jointAccount">Joint Account</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.bankName">Bank Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.openingDate">Opening Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.closedDate">Closed Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankDetails.client">Client</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bankDetailsList.map((bankDetails, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${bankDetails.id}`} color="link" size="sm">
                        {bankDetails.id}
                      </Button>
                    </td>
                    <td>{bankDetails.accountHolderName}</td>
                    <td>{bankDetails.accountNumber}</td>
                    <td>{bankDetails.sortCode}</td>
                    <td>{bankDetails.jointAccount ? 'true' : 'false'}</td>
                    <td>{bankDetails.bankName}</td>
                    <td>
                      <TextFormat type="date" value={bankDetails.openingDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={bankDetails.closedDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{bankDetails.client ? <Link to={`client/${bankDetails.client.id}`}>{bankDetails.client.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${bankDetails.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${bankDetails.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${bankDetails.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.bankDetails.home.notFound">No Bank Details found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ bankDetails }: IRootState) => ({
  bankDetailsList: bankDetails.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BankDetails);
