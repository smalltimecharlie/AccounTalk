import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employment-details.reducer';
import { IEmploymentDetails } from 'app/shared/model/employment-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmploymentDetailsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class EmploymentDetails extends React.Component<IEmploymentDetailsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { employmentDetailsList, match } = this.props;
    return (
      <div>
        <h2 id="employment-details-heading">
          <Translate contentKey="accounTalkApp.employmentDetails.home.title">Employment Details</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.employmentDetails.home.createLabel">Create new Employment Details</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {employmentDetailsList && employmentDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.employmentDetails.taxReturn">Tax Return</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.employmentDetails.employment">Employment</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {employmentDetailsList.map((employmentDetails, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${employmentDetails.id}`} color="link" size="sm">
                        {employmentDetails.id}
                      </Button>
                    </td>
                    <td>
                      {employmentDetails.taxReturn ? (
                        <Link to={`tax-return/${employmentDetails.taxReturn.id}`}>{employmentDetails.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {employmentDetails.employment ? (
                        <Link to={`employment/${employmentDetails.employment.id}`}>{employmentDetails.employment.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${employmentDetails.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employmentDetails.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employmentDetails.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.employmentDetails.home.notFound">No Employment Details found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ employmentDetails }: IRootState) => ({
  employmentDetailsList: employmentDetails.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmploymentDetails);
