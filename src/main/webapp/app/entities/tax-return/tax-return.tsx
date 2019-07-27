import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './tax-return.reducer';
import { ITaxReturn } from 'app/shared/model/tax-return.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITaxReturnProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class TaxReturn extends React.Component<ITaxReturnProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { taxReturnList, match } = this.props;
    return (
      <div>
        <h2 id="tax-return-heading">
          <Translate contentKey="accounTalkApp.taxReturn.home.title">Tax Returns</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.taxReturn.home.createLabel">Create new Tax Return</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {taxReturnList && taxReturnList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.taxReturn.year">Year</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.taxReturn.studentLoan">Student Loan</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.taxReturn.bankRefundDetails">Bank Refund Details</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.taxReturn.statePensionDetails">State Pension Details</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.taxReturn.client">Client</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {taxReturnList.map((taxReturn, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${taxReturn.id}`} color="link" size="sm">
                        {taxReturn.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={taxReturn.year} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <Translate contentKey={`accounTalkApp.StudentLoan.${taxReturn.studentLoan}`} />
                    </td>
                    <td>
                      {taxReturn.bankRefundDetails ? (
                        <Link to={`bank-details/${taxReturn.bankRefundDetails.id}`}>{taxReturn.bankRefundDetails.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {taxReturn.statePensionDetails ? (
                        <Link to={`state-pension-received/${taxReturn.statePensionDetails.id}`}>{taxReturn.statePensionDetails.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{taxReturn.client ? <Link to={`client/${taxReturn.client.id}`}>{taxReturn.client.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${taxReturn.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${taxReturn.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${taxReturn.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.taxReturn.home.notFound">No Tax Returns found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ taxReturn }: IRootState) => ({
  taxReturnList: taxReturn.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TaxReturn);
