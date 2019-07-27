import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employment-details.reducer';
import { IEmploymentDetails } from 'app/shared/model/employment-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmploymentDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmploymentDetailsDetail extends React.Component<IEmploymentDetailsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employmentDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.employmentDetails.detail.title">EmploymentDetails</Translate> [
            <b>{employmentDetailsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <Translate contentKey="accounTalkApp.employmentDetails.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{employmentDetailsEntity.taxReturn ? employmentDetailsEntity.taxReturn.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.employmentDetails.employment">Employment</Translate>
            </dt>
            <dd>{employmentDetailsEntity.employment ? employmentDetailsEntity.employment.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/employment-details" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/employment-details/${employmentDetailsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employmentDetails }: IRootState) => ({
  employmentDetailsEntity: employmentDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmploymentDetailsDetail);
