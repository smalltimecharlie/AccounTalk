import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employment.reducer';
import { IEmployment } from 'app/shared/model/employment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmploymentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Employment extends React.Component<IEmploymentProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { employmentList, match } = this.props;
    return (
      <div>
        <h2 id="employment-heading">
          <Translate contentKey="accounTalkApp.employment.home.title">Employments</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.employment.home.createLabel">Create new Employment</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {employmentList && employmentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.employment.businessName">Business Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.employment.payeReference">Paye Reference</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.employment.employmentEndDate">Employment End Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.employment.client">Client</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {employmentList.map((employment, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${employment.id}`} color="link" size="sm">
                        {employment.id}
                      </Button>
                    </td>
                    <td>{employment.businessName}</td>
                    <td>{employment.payeReference}</td>
                    <td>
                      <TextFormat type="date" value={employment.employmentEndDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{employment.client ? <Link to={`client/${employment.client.id}`}>{employment.client.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${employment.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employment.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${employment.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.employment.home.notFound">No Employments found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ employment }: IRootState) => ({
  employmentList: employment.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Employment);
