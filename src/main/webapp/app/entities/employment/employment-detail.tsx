import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employment.reducer';
import { IEmployment } from 'app/shared/model/employment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmploymentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmploymentDetail extends React.Component<IEmploymentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employmentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.employment.detail.title">Employment</Translate> [<b>{employmentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="businessName">
                <Translate contentKey="accounTalkApp.employment.businessName">Business Name</Translate>
              </span>
            </dt>
            <dd>{employmentEntity.businessName}</dd>
            <dt>
              <span id="payeReference">
                <Translate contentKey="accounTalkApp.employment.payeReference">Paye Reference</Translate>
              </span>
            </dt>
            <dd>{employmentEntity.payeReference}</dd>
            <dt>
              <span id="employmentEndDate">
                <Translate contentKey="accounTalkApp.employment.employmentEndDate">Employment End Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={employmentEntity.employmentEndDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="accounTalkApp.employment.client">Client</Translate>
            </dt>
            <dd>{employmentEntity.client ? employmentEntity.client.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/employment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/employment/${employmentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ employment }: IRootState) => ({
  employmentEntity: employment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmploymentDetail);
