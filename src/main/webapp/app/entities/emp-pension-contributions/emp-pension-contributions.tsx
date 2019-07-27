import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './emp-pension-contributions.reducer';
import { IEmpPensionContributions } from 'app/shared/model/emp-pension-contributions.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmpPensionContributionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EmpPensionContributions extends React.Component<IEmpPensionContributionsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { empPensionContributionsList, match } = this.props;
    return (
      <div>
        <h2 id="emp-pension-contributions-heading">
          <Translate contentKey="accounTalkApp.empPensionContributions.home.title">Emp Pension Contributions</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.empPensionContributions.home.createLabel">Create new Emp Pension Contributions</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {empPensionContributionsList && empPensionContributionsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.empPensionContributions.amountPaid">Amount Paid</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.empPensionContributions.employmentDetails">Employment Details</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {empPensionContributionsList.map((empPensionContributions, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${empPensionContributions.id}`} color="link" size="sm">
                        {empPensionContributions.id}
                      </Button>
                    </td>
                    <td>{empPensionContributions.amountPaid}</td>
                    <td>
                      {empPensionContributions.employmentDetails ? (
                        <Link to={`employment-details/${empPensionContributions.employmentDetails.id}`}>
                          {empPensionContributions.employmentDetails.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${empPensionContributions.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${empPensionContributions.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${empPensionContributions.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.empPensionContributions.home.notFound">No Emp Pension Contributions found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ empPensionContributions }: IRootState) => ({
  empPensionContributionsList: empPensionContributions.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmpPensionContributions);
