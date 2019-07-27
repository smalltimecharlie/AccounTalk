import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './bank-interest.reducer';
import { IBankInterest } from 'app/shared/model/bank-interest.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBankInterestProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BankInterest extends React.Component<IBankInterestProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { bankInterestList, match } = this.props;
    return (
      <div>
        <h2 id="bank-interest-heading">
          <Translate contentKey="accounTalkApp.bankInterest.home.title">Bank Interests</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.bankInterest.home.createLabel">Create new Bank Interest</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {bankInterestList && bankInterestList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankInterest.netInterest">Net Interest</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankInterest.taxDeducted">Tax Deducted</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankInterest.taxReturn">Tax Return</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.bankInterest.bankdetails">Bankdetails</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bankInterestList.map((bankInterest, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${bankInterest.id}`} color="link" size="sm">
                        {bankInterest.id}
                      </Button>
                    </td>
                    <td>{bankInterest.netInterest}</td>
                    <td>{bankInterest.taxDeducted}</td>
                    <td>
                      {bankInterest.taxReturn ? (
                        <Link to={`tax-return/${bankInterest.taxReturn.id}`}>{bankInterest.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {bankInterest.bankdetails ? (
                        <Link to={`bank-details/${bankInterest.bankdetails.id}`}>{bankInterest.bankdetails.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${bankInterest.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${bankInterest.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${bankInterest.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.bankInterest.home.notFound">No Bank Interests found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ bankInterest }: IRootState) => ({
  bankInterestList: bankInterest.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BankInterest);
