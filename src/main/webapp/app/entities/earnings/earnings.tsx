import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './earnings.reducer';
import { IEarnings } from 'app/shared/model/earnings.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEarningsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Earnings extends React.Component<IEarningsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { earningsList, match } = this.props;
    return (
      <div>
        <h2 id="earnings-heading">
          <Translate contentKey="accounTalkApp.earnings.home.title">Earnings</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.earnings.home.createLabel">Create new Earnings</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {earningsList && earningsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.earnings.grossPay">Gross Pay</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.earnings.taxDeducted">Tax Deducted</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.earnings.studentLoanDeductions">Student Loan Deductions</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.earnings.employmentDetails">Employment Details</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {earningsList.map((earnings, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${earnings.id}`} color="link" size="sm">
                        {earnings.id}
                      </Button>
                    </td>
                    <td>{earnings.grossPay}</td>
                    <td>{earnings.taxDeducted}</td>
                    <td>{earnings.studentLoanDeductions}</td>
                    <td>
                      {earnings.employmentDetails ? (
                        <Link to={`employment-details/${earnings.employmentDetails.id}`}>{earnings.employmentDetails.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${earnings.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${earnings.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${earnings.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.earnings.home.notFound">No Earnings found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ earnings }: IRootState) => ({
  earningsList: earnings.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Earnings);
